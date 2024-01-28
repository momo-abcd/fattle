import { API } from '../../config';
import axios from 'axios';

const getMypage = async (userCode) => {
  const { data } = await axios.get(API.MYPAGE_GET + userCode);
  return data;
};
const putMypage = async (userCode, parameter) => {
  const { status } = await axios.patch(API.MYPAGE_GET + userCode, parameter);
  return status;
};

const getFollowingList = async (userCode) => {
  const { data } = await axios.get(API.FOLLOWING_GET + userCode);
  return data;
};
const getFollowerList = async (userCode) => {
  const { data } = await axios.get(API.FOLLOWER_GET + userCode);
  return data;
};
// const putMypage = async ()
export { getMypage, putMypage, getFollowingList, getFollowerList };
