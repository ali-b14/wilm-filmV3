import Comment from 'app/entities/comment/comment';
import React, { useEffect, useState } from 'react';

interface Comment {
  id: string;
  text: string;
  author: string;
  video: string;
}

function commentSection() {
  const [comments, setComments] = useState<Comment[]>([]);
  const [commentBody, setCommentBody] = useState('');

  useEffect(() => {
    FetchComment()
      .then(data => {
        setComments(data);
        // eslint-disable-next-line no-console
        console.log(data);
      })
      .catch(error => {
        console.error('Error fetching movies', error);
      });
  }, []);

  async function FetchComment(): Promise<Comment[]> {
    try {
      const response = await fetch('/api/comments');
      if (!response.ok) {
        throw new Error('Failed to fetch comments');
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching comments', error);
      throw error;
    }
  }

  const submitComment = () => {
    fetch('/api/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ text: commentBody }),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to submit comment');
        }
        return FetchComment();
      })
      .then(() => {
        setCommentBody('');
      })
      .catch(error => {
        console.error('Error submitting comment', error);
      });
  };

  // const onComment = () => {
  //   const newComment: Comment = {
  //     id: String(comments.length + 1),
  //     text: commentBody,
  //   };

  //   setComments(prev => [newComment, ...prev]);
  //   setCommentBody("");
  // }

  return (
    <div>
      <input
        value={commentBody}
        onChange={event => setCommentBody(event.target.value)}
        placeholder="Leave a Comment"
        // className="border-[1px]"
      />
      <span>
        <button onClick={submitComment}>Submit</button>
      </span>
      <div className="comment-section">
        {comments.map(comment => (
          <div className="comments" key={comment.id}>
            {comment.text}
          </div>
        ))}
      </div>
    </div>
  );
}

export default commentSection;
