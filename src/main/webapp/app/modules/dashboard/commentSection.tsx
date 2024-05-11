import './commentSection.scss';

import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router';

interface Comment {
  id: string;
  text: string;
  posted_at: string;
  author: string;
  video: string;
}

function CommentSection() {
  const [comments, setComments] = useState<Comment[]>([]);
  const [commentBody, setCommentBody] = useState('');

  const { id } = useParams<{ id: string }>();

  useEffect(() => {
    fetchComments();
  }, []);

  const fetchComments = async () => {
    try {
      const response = await fetch('/api/comments');
      if (!response.ok) {
        throw new Error('Failed to fetch comments');
      }
      const data = await response.json();
      setComments(data);
    } catch (error) {
      console.error('Error fetching comments', error);
    }
  };

  const currentUser = useSelector((state: any) => state.authentication.account);

  const submitComment = async () => {
    try {
      const response = await fetch('/api/comments', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          text: commentBody,
          postedAt: new Date().toISOString(),
          author: { id: currentUser.id, login: currentUser.login },
          video: { id },
        }),
      });

      if (!response.ok) {
        throw new Error('Failed to submit comment');
      }

      const newComment = await response.json();
      setComments(prevComments => [newComment, ...prevComments]);
      setCommentBody('');
    } catch (error) {
      console.error('Error submitting comment', error);
    }
  };

  return (
    <div>
      <input value={commentBody} onChange={event => setCommentBody(event.target.value)} placeholder="Leave a Comment" />
      <button className="submit-button" onClick={submitComment as any}>
        Submit
      </button>
      <div>
        {comments.map(comment => (
          <div className="comments" key={comment.id}>
            {comment.text}
          </div>
        ))}
      </div>
    </div>
  );
}

export default CommentSection;
