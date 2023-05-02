var fs = require('fs');

require.extensions['.txt'] = function (module, filename) {
    module.exports = fs.readFileSync(filename, 'utf8');
};

var changes = require("./changes.txt").toString();

let result = changes.split('\n').map((q) => {
    return /([^\/]+)(\.java)/.test(q) ? `${/([^\/]+)(\.java)/.exec(q)[0]},${/[0-9]+/.test(q) ? /[0-9]+/.exec(q)[0] : ''}` : null;
}).filter(q => q !== null).join('\n');

fs.writeFile("changeMetrics.csv", result, function (err) {
    if (err) {
        return console.log(err);
    }
    console.log("The file was saved!");
});