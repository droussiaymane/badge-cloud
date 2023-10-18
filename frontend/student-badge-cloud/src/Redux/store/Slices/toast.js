import { createSlice } from '@reduxjs/toolkit';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const initialState = {
  succesMessage: '',
  errorMessage: '',
};

const toastSlice = createSlice({
  name: 'toast',
  initialState,
  reducers: {
    succesMessage(state, actions) {
      state.succesMessage = actions.payload.message;
      toast.success(actions.payload.message, {
        position: toast.POSITION.TOP_CENTER,
      });
      <ToastContainer />;
    },
    errorMessage(state, actions) {
      state.errorMessage = actions.payload.message;
      toast.error(actions.payload.message, {
        position: toast.POSITION.TOP_CENTER,
      });
      <ToastContainer />;
    },
  },
});

export const toastActions = toastSlice.actions;
export default toastSlice.reducer;
