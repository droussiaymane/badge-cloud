const logger = (type, msg) => {
  if (process.env.REACT_APP_ENVIRONTMENT === 'DEVELOPMENT') {
    if (type === 'error') {
      console.error(msg);
    } else if (type === 'warning') {
      console.warning(msg);
    } else {
      console.log(msg);
    }
  }
};

export default logger;
