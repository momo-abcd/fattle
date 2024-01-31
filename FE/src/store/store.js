import { configureStore, createSlice } from '@reduxjs/toolkit';

const userCode = createSlice({
  name: 'userCode',
  initialState: {
    userCode: '132',
  },
  reducers: {
    changeUserCode(state, action) {
      state.userCode = action.payload;
    },
  },
});

export const { changeUserCode } = userCode.actions;

export default configureStore({
  reducer: {
    userCode: userCode.reducer,
  },
});
