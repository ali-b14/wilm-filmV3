import axios from 'axios';

const API_URL = 'api/likes';

export const createLike = (like: { likedAt: string; video: { id: number }; user: { id: number } }) => {
  return axios.post(API_URL, like);
};
