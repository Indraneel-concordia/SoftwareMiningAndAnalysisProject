const fs = require('fs');
const fastcsv = require('fast-csv');
const opn = require('opn');

require.extensions['.txt'] = function (module, filename) {
    module.exports = fs.readFileSync(filename, 'utf8');
};

const defectFile = require('./PreReleaseDefects.txt');
const defectArray = defectFile.split(',');

const count = {};
defectArray.forEach(function(fileName) {
    count[fileName] = (count[fileName] || 0) + 1;
});

const result = Object.keys(count).map(fileName => `${fileName},${count[fileName]}`).join('\n');
fs.writeFile('PreRleaseDefects.csv', result, function(err) {
    if (err) {
        return console.log(err);
    }

    console.log('The file was saved!');
});
