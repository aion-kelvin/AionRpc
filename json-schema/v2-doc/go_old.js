const $RefParser = require('json-schema-ref-parser');

const lineReader = require('readline').createInterface({
      input: require('fs').createReadStream('method_list.txt')
});

/**
 * @param {object} schema of parameter
 */
const docParameterType = async function(schema) { 
    await schema;
    output = "";

    if(typeof schema.type !== 'undefined') {
        output += schema.type;
    } else if(typeof schema.anyOf !== 'undefined') { 
        for(ix = 0; ix < schema.anyOf.length; ++ix) { 
            output += await docParameterType(schema.anyOf[ix]);
            if(ix != schema.anyOf.length - 1) { 
                output += '|'
            }
        }
    } else { 
        console.log("uh oh");
        throw new Error("Don't know how to parse given schema."); 
    }

    return output;
}

/**
 * @param {object} schema of request
 */
const docParameters = async function(request) {
    var output = "";
    console.log(request.items.length);
    /*for(ix = 0; ix < request.items.length; ++ix) { 
        console.log(ix);
        var item = await request.items[ix];
        output += "1." + " " + await docParameterType(item) + ', ' + item.description;
        output += "\n";
    }*/
    for(ix = 0; ix < 3; ++ix) { 
        console.log("start ix = " + ix);

        var item;
        try{
          item  = await request.items[ix];
        } catch (Err) { 
            console.log("big problemz");
        }
        var param;
        /*try {
          param = await docParameterType(item);
        } catch (Err) {
            console.log("big problemz");
        }*/

        /*
        output += "1." + " " + await docParameterType(item) + ', ' + item.description;*/
        output += ix;
        output += "\n";

        console.log("end ix = " + ix);
    }
    return output;
};


const doc = async function(method) {
	let parser = new $RefParser();
	let request;
    let output = "";

    try { 
        request = await parser.dereference('schema/' + method + '.request.json');
    } catch (err) { 
        console.log("Derp de derp can't load request file.");
    }

    output += '### ' + method;
    output += "\n";
    output += request.description;
    output += "\n";
    output += "#### Parameters ";
    output += "\n";

    try { 
        await docParameters(request);
    } catch (err) { 
        console.log("error");
    }
};

lineReader.on('line', async function (method) {
    try {
        await doc(method);
    } catch (err) { 
        console.log(err);
    }
    //console.log('Line from file:', line);
});


