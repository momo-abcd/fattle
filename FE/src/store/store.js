import { configureStore } from '@reduxjs/toolkit';
import mypageSlice from './mypageSlice';

const store = configureStore({
  reducer: {
    mypage: mypageSlice.reducer,
  },
});
export default store;
