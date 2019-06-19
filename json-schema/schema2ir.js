/**
 * Take the JSON-schema describing Aion RPC API and output an 
 * intermediate JSON representation that is suitable for markdown
 * API docs generation and test case generation.  The intermediate format
 * is as follows:
 *
 * First, define a type of JSON object with the following structure ParamDescriptor:
 *
 *   ParamDescriptor := {
 *
 *       // the param accepts any of these types; each 
 *       // jsontype is a json primitive or a JSON-Schema $ref 
 *       type: [jsontype1, jsontype2, ...] 
 *
 *       // description will be the parameter description in the docs
 *       description: string
 *
 *       // if one of the jsontypes from type was an object, the list below
 *       // is the list of properties in the object.  SimpleParamDescriptor
 *       // is a JSON object that is the same as ParamDescriptor, but without obj_props
 *       //
 *       // use-case limitations: can't represent a param that accepts more than one kind
 *       // of object (i.e. type has object twice, each with different schema); can't
 *       // represent objects nested within objects.
 *       obj_props: [ SimpleParamDescriptor1, SimpleParamDescriptor2, ... ]
 *
 *       // whether parameter is optional
 *       optional: boolean
 *   }
 *
 * For each RPC API method, represent it with the following object structure:
 *
 *   MethodDescriptor := {
 *
 *       // i.e. eth_getBlockByNumber
 *       methodName: string 
 *
 *       // description for the method
 *       description: string
 *
 *       // parameters
 *       params: [ ParamDescriptor1, ParamDescriptor2, ... ] 
 *
 *       // returns
 *       returns: ParamDescriptor
 *
 *       // example of the params array suitable for invoking the method.
 *       // array elements can be of any type
 *       param_example: [ Any1, Any2, Any3 ]
 *
 *       // the return value corresponding to example, can be of any type
 *       returns_example: Any
 *   }
 */

const sw = require('@cloudflare/json-schema-walker');
const fs = require("fs");
const util = require("util");

const rpcMethod = "eth_getBlockByNumber"; // Parameterize me 

const paramRoot = JSON.parse(fs.readFileSync("v2-doc/schema/" + rpcMethod + ".request.json"));
const retRoot = JSON.parse(fs.readFileSync("v2-doc/schema/" + rpcMethod + ".response.json"));

// we'll build up this object and print it out at the end
let md = {
    "methodName": rpcMethod,
    "params": [],
    "returns": {},
    "params_example": null,
    "returns_example": null
};

/** returns an array of strings */
var nonRecursivePrettyTypeName = (subschema) => { 
    //console.debug('nonRecursivePrettyTypeName ', subschema);
    
    if(subschema.hasOwnProperty('type')) {
        if(subschema.type == 'object') { 
            return ["object"];
        }
        return [subschema.type];
    } else if(subschema.hasOwnProperty('$ref')) { 
        return [subschema.$ref]
    } else if(subschema.hasOwnProperty('anyOf')) { 
        // assumes no nested anyOf so get [0] on the recursive call all the time 
        return subschema.anyOf.map(t => nonRecursivePrettyTypeName(t)[0]);
    } else { 
        throw new Error("Don't know how to deduce RPC method type at subschema: " 
            + subschema);
    }
}

/** The callback for each node visited in the parameters schema */
var paramsPreFunc = (subschema, path, schema, parentPath) => { 
    //console.debug("preFunc2 path=", path, " parentPath=", parentPath, " parentPath.length=", parentPath.length, " path.length=", path.length, " path[0]=", path[0]);

    // root node: get method information, print some title junk
    if(parentPath.length == 0 && path.length == 0) { 
        md.description = subschema.description;
        md.params_example = subschema.hasOwnProperty('examples') && subschema.examples.length >= 1 ? 
            subschema.examples[0] : null;
    }

    // we are in a particular item in the method parameters list
    if(parentPath.length == 0 && path.length == 2 && path[0] == 'items') {
        md.params.push({
            "type": nonRecursivePrettyTypeName(subschema),
            "description": subschema.description,
            "obj_props": subschema.type == 'object' ? [] : null,
            "optional": subschema.hasOwnProperty('optional') && subschema.optional
        });
    }
}

var retPreFunc = (subschema, path, schema, parentPath) => { 
    //console.debug("retPreFunc path=", path, " parentPath=", parentPath, " parentPath.length=", parentPath.length, " path.length=", path.length, " path[0]=", path[0]);

    // root node
    if(parentPath.length == 0 && path.length == 0) { 
        md.returns.type = nonRecursivePrettyTypeName(subschema);
        md.returns.description = subschema.description;
        md.returns.optional = subschema.hasOwnProperty('optional') && subschema.optional;
        if(subschema.type == 'object') { 
            md.returns.obj_props = [];
        }

        md.returns_example = subschema.hasOwnProperty('examples') && subschema.examples.length >= 1 ? 
            subschema.examples[0] : null;
    }

    // the return type is object and we are in a particular property of it 
    if(parentPath.length == 0 && path.length == 2 && path[0] == 'properties') {
        md.returns.obj_props.push({
            "name": path[1],
            "type": nonRecursivePrettyTypeName(subschema),
            "description": subschema.description,
            "optional": subschema.hasOwnProperty('optional') && subschema.optional
        });
    }
}

sw.schemaWalk( paramRoot, paramsPreFunc );
sw.schemaWalk( retRoot, retPreFunc );

//console.log(util.inspect(md, false, null, true));
console.log(JSON.stringify(md));
