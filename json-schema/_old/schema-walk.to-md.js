const sw = require('@cloudflare/json-schema-walker');
const fs = require("fs");

const rpcMethod = "eth_getBlockByNumber"; // Parameterize me 

const paramRoot = JSON.parse(fs.readFileSync("v2-doc/schema/" + rpcMethod + ".request.json"));
const retRoot = JSON.parse(fs.readFileSync("v2-doc/schema/" + rpcMethod + ".response.json"));

var nonRecursivePrettyTypeName = (subschema) => { 
    //console.debug('nonRecursivePrettyTypeName ', subschema);

    if(subschema.hasOwnProperty('type')) {
        var text = "";
        if(subschema.type == 'object') { 
            return "object";
        }
        return subschema.type;
    } else if(subschema.hasOwnProperty('$ref')) { 
        split = subschema.$ref.split('/');
        return split[split.length - 1];
    } else if(subschema.hasOwnProperty('anyOf')) { 
        // assumes no nested anyOf 
        var anyOfText = "";
        for(let ix = 0; ix < subschema.anyOf.length; ++ix) { 
            //anyOfText += subschema.anyOf[ix];
            anyOfText += nonRecursivePrettyTypeName(subschema.anyOf[ix]);
            if(ix != subschema.anyOf.length - 1) { 
                anyOfText += "|";
            }
        }
        return anyOfText;
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
        console.log("### " + rpcMethod);
        console.log();
        console.log(subschema.description);
        console.log();
        console.log("#### Parameters");
        console.log();
    }

    // we are in a particular item in the method parameters list
    if(parentPath.length == 0 && path.length == 2 && path[0] == 'items') {
        var typeText = "1. ```" + nonRecursivePrettyTypeName(subschema) + "``` - ";

        // description
        if(subschema.hasOwnProperty('optional') && subschema.optional) { 
            typeText += "(optional) ";
        }
        typeText += subschema.description;
        console.log(typeText);

    }
}

var retPreFunc = (subschema, path, schema, parentPath) => { 
    //console.debug("retPreFunc path=", path, " parentPath=", parentPath, " parentPath.length=", parentPath.length, " path.length=", path.length, " path[0]=", path[0]);

    // root node: get method information, print some title junk
    if(parentPath.length == 0 && path.length == 0) { 
        //console.debug("Xxx ");
        //console.debug(subschema);

        console.log();
        console.log("#### Returns");
        console.log();

        console.log('```' + nonRecursivePrettyTypeName(subschema) + '``` - ' + subschema.description);
        console.log();
    }

    // the return type is object and we are in a particular property of it 
    if(parentPath.length == 0 && path.length == 2 && path[0] == 'properties') {
        var typeText = "- ```" + nonRecursivePrettyTypeName(subschema) + "``` - ";

        // description
        if(subschema.hasOwnProperty('optional') && subschema.optional) { 
            typeText += "(optional) ";
        }
        typeText += subschema.description;
        console.log(typeText);

    }
}



sw.schemaWalk( paramRoot, paramsPreFunc );
sw.schemaWalk( retRoot, retPreFunc );

console.log(paramRoot.example);
