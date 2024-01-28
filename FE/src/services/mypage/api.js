import { API } from '../../config';
import axios from 'axios';

const getMypage = async (userCode) => {
  const { data } = await axios.get(API.MYPAGE_GET + userCode);
  return data;
};
// const putMypage = async ()
export { getMypage };
