import { createSlice } from '@reduxjs/toolkit';

const mypageSlice = createSlice({
  name: 'mypage',
  initialState: {
    mypageGetData: {},
    mypageGetStatus: '',
  },
  reducers: {
    getMypage: (state, action) => {
      state.mypageGetData = action.payload;
    },
    modifyMypage: (state, action) => {},
    getCalendarDetail: (state, action) => {},
    getFollowing: (state, action) => {},
    getFollerwer: (state, action) => {},
    getGoal: (state, action) => {},
  },
});
export const { modifyMypage, getCalendarDetail, getFollowing, getGoal } =
  mypageSlice.actions;
export default mypageSlice;
