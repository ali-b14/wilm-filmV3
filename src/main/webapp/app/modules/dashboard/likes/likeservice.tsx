import axios from 'axios';

const API_URL = 'api/likes';

interface User {
  id: number;
}
interface Video {
  id: number;
}
interface LikeDTO {
  user: User;
  video: Video;
  liked: string;
}

export const createLike = (like: { likedAt: string; video: { id: number }; user: { id: number } }) => {
  return axios.post(API_URL, like);
};
export const countLikes = (videoId: number) => {
  return axios.get(`${API_URL}/count`, {
    params: {
      videoId,
    },
  });
};
