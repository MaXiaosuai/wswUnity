var exec = require('cordova/exec');

module.exports = {
    openMiniProgram:function (arg0, success, error) {
        exec(success, error, 'wswUnity', 'openMiniProgram', [arg0]);
    }
}
