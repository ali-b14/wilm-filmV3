import './dashboard.scss';
import React, { useEffect, useState } from 'react';
import FetchMovies from './fetchMovie';
import { useParams } from 'react-router';
import CommentSection from './commentSection';
import Likebutton from 'app/modules/dashboard/likes/likebutton';

interface MovieProps {
  trailerUrl: string;
}

//PascalCasing
const Movie = () => {
  const [movieData, setMovieData] = useState([]);
  const [clickedMovieId] = useState(Number);
  const [clickedMovieUrl, setClickedMovieUrl] = useState('');
  const { id } = useParams<{ id: string }>();

  useEffect(() => {
    FetchMovies()
      .then(data => {
        setMovieData(data);
        // eslint-disable-next-line no-console
        console.log(data);
      })
      .catch(error => {
        console.error('Error fetching movies', error);
      });
  }, []);

  useEffect(() => {
    // eslint-disable-next-line no-console
    console.log(id);
    `/commentSection/${clickedMovieId}`;
  }, [clickedMovieId]);

  // eslint-disable-next-line no-console
  console.log(id);
  const selectedMovie = movieData.find(movie => movie.id === Number(id));

  return (
    <div>
      {selectedMovie && (
        <div className="container-movie-screen">
          <span className="content-wrapper">
            <iframe
              title="Embedded Content"
              src={selectedMovie.url}
              frameBorder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            ></iframe>
            <span className="comment-section">
              <CommentSection />
            </span>
          </span>
        </div>
      )}
    </div>
  );
};

export default Movie;
