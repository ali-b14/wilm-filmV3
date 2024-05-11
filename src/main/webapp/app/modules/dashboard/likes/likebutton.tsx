import React, { useState } from 'react';
import { AiFillLike, AiFillDislike } from 'react-icons/ai';
import { createLike } from './likeservice'; // Ensure the path is correct based on your project structure

interface LikeButtonProps {
  userId: number;
  videoId: number;
}

const LikeButton: React.FC<LikeButtonProps> = ({ userId, videoId }) => {
  const [liked, setLiked] = useState(false);

  const handleClick = async () => {
    console.log('Button clicked'); // Debugging log
    if (!liked) {
      try {
        const likedAt = new Date().toISOString();
        const like = { user: { id: userId }, video: { id: videoId }, likedAt };
        await createLike(like);
        setLiked(true);
        console.log('Like created'); // Debugging log
      } catch (error) {
        console.error('Error liking the video:', error);
      }
    }
  };

  return (
    <button onClick={handleClick} style={{ border: 'none', background: 'none' }}>
      {liked ? <AiFillLike color="blue" size="50" /> : <AiFillDislike color="red" size="50" />}
    </button>
  );
};

export default LikeButton;
