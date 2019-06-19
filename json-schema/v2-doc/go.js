const $RefParser = require('json-schema-ref-parser');

const lineReader = require('readline').createInterface({
      input: require('fs').createReadStream('method_list.txt')
});

/**
 * @param {object} schema of parameter
 */
const docParameterType = function(schema) { 
    output = "";
    
    if(typeof schema.type !== 'undefined') {
        output += schema.type;
    } else if(typeof schema.anyOf !== 'undefined') { 
        for(var ix = 0; ix < schema.anyOf.length; ++ix) { 
            output += docParameterType(schema.anyOf[ix]);
            if(ix != schema.anyOf.length + 1) { 
                output += "|"
            }
        }
    } else { 
        console.log("uh oh");
        return "UNKNOWN";
        //throw new Error("Don't know how to parse given schema."); 
    }

    return output;
}

/**
 * @param {object} schema of request
 */
const docParameters = function(request) {
    var output = "";
    for(var ix = 0; ix < request.items.length; ++ix) { 
        var item = request.items[ix];
        output += ix + 1 + ". ";
        output += '```' + docParameterType(item) + '```';
        output += ', ' + item.description;
        output += "\n";
    }
    return output;
};

const docReturns = function(response) { 
    var output = "";
    for(var ix = 0; ix < request.items.length; ++ix) { 
        var item = request.items[ix];
        output += ix + 1 + ". ";
        output += '```' + docParameterType(item) + '```';
        output += ', ' + item.description;
        output += "\n";
    }
    return output;
}

const doc = async function(method) {
	let parser = new $RefParser();

	let request;
    let response;
    try { 
        request = await parser.dereference('schema/' + method + '.request.json');
        response = await parser.dereference('schema/' + method + '.response.json');
    } catch (err) { 
        console.log("Derp de derp can't load request file.");
    }

    console.log('### ' + method);
    console.log();
    console.log(request.description);
    console.log();

    console.log("#### Parameters ");
    console.log();
    console.log(docParameters(request));

    console.log("#### Returns ");
    console.log();
    console.log(docParameters(response));

};

doc("eth_getBlockByNumber");

/*lineReader.on('line', function (method) {
    try {
        doc(method);
    } catch (err) { 
        console.log(err);
    }
    //console.log('Line from file:', line);
});*/


