import jwtDecode from 'jwt-decode';
const clientValidateActionMiddleware = store => next => action => {
  if (action.meta && action.meta.fromServer) {
    return next(action)
  }
  throw new Error('Invalid action from client')
}

const validateActionMiddleware = store => next => action => {
  if (!action.type || !action.payload) {
    throw new Error('Invalid action format')
  }
  next(action)
}


const authenticationMiddleware = (store) => (next) => (action) => {
  const result = next(action);
  const state = store.getState();
  if (action.type === "TOKEN_CHANGED") {
    if (state.token) {
      // check token is valid
      if (isValidToken(state.token)) {
        store.dispatch({ type: "AUTHENTICATED" })
      } else {
        store.dispatch({ type: "LOGOUT" })
        //   redirectToLogin();
      }
    }
  }
  return result;
};

function isValidToken(token) {
  if (!token) {
    return false;
  }
  const decoded = jwtDecode(token);
  if (!decoded || !decoded.exp) return true;
  const currentTime = Date.now() / 1000;
  return decoded.exp > currentTime;
}
export {
  clientValidateActionMiddleware,
  validateActionMiddleware,
  authenticationMiddleware,
}