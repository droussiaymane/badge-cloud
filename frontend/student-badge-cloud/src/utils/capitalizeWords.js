const CapitalizeWords = (str) => {
    return str.toLowerCase().replace(/(^|\s)\S/g, function (l) { return l.toUpperCase(); });
}
export default CapitalizeWords;