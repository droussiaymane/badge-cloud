import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    sideBar: true,
};

const sidebarSlice = createSlice({
    name: 'sidebar',
    initialState,
    reducers: {
        showSidebar(state, actions) {
            state.sideBar = actions.payload.value;
        }
    },
});

export const sidebarActions = sidebarSlice.actions;
export default sidebarSlice.reducer;