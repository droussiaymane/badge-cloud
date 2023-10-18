import { createSlice } from '@reduxjs/toolkit';

const initialState = {};

const standDardSlice = createSlice({
  name: 'standard',
  initialState,
  reducers: {},
});

export const standardActions = standDardSlice.actions;
export default standDardSlice.reducer;
