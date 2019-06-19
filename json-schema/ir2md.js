const fs = require("fs");

const rpcMethod = "eth_getBlockByNumber"; // Parameterize me 
const md = JSON.parse(fs.readFileSync("ir/" + rpcMethod + ".ir.json"));

const schemaCustomTypePrettyNames = {
    'DATA32': 'DATA, 32 Bytes',
    'DATA256': 'DATA, 256 Bytes',
    'DATA1408': 'DATA, 1048 Bytes'
};

const prettyAtomicTypeName = (uglyTypeName) => { 
    if(uglyTypeName.includes('/')) { 
        // custom defined types 
        let split = uglyTypeName.split('/');
        let maybePretty = split[split.length - 1];

        if(schemaCustomTypePrettyNames.hasOwnProperty(maybePretty)) {
            return schemaCustomTypePrettyNames[maybePretty];
        } else { 
            return maybePretty;
        }
    } else { 
        // javascript built-in types
        let c0 = uglyTypeName.charAt(0).toUpperCase(); 
        return c0 + uglyTypeName.slice(1);
    }
}

const prettyTypeList = (schemaTypeList) => { 
    if(schemaTypeList.length == 1) { 
        return prettyAtomicTypeName(schemaTypeList[0]);
    } else { 
        var ret = '';
        for(let ix = 0; ix < schemaTypeList.length; ++ix) { 
            // implicitly assuming there's no nested anyOf
            ret += prettyAtomicTypeName(schemaTypeList[ix]);
            if(ix != schemaTypeList.length - 1) { 
                ret += '|';
            }
        }
        return ret;
    }
}

console.log('### ' + rpcMethod);
console.log();
console.log(md.description);
console.log();

console.log('#### Parameters');
console.log();
md.params.forEach( (e) => { 
    var optional = e.hasOwnProperty('optional') && e.optional ? "(optional) " : "";
    console.log('1. ```' + prettyTypeList(e.type) + '``` - ' + optional + e.description);
});
console.log();

console.log('#### Returns');
console.log();
console.log('```' + prettyTypeList(md.returns.type) + '``` - ' + md.returns.description);
console.log();
if(md.returns.hasOwnProperty('obj_props')) { 
    md.returns.obj_props.forEach( (e) => { 
        var optional = e.hasOwnProperty('optional') && e.optional ? "(optional) " : "";
        console.log('- ' + e.name + ': ```' + prettyTypeList(e.type) + '``` - ' + optional + e.description);
    });
}


console.log('#### Example ');
console.log();
console.log('##### Request');
console.log();
// TODO put jsonrpc envelope thing
console.log('```');
console.log(JSON.stringify(md.params_example, null, 2));
console.log('```');
console.log();
// TODO put jsonrpc envelope thing
console.log('##### Response ');
console.log();
console.log('```');
console.log(JSON.stringify(md.returns_example, null, 2));
console.log('```');


