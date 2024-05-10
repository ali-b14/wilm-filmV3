import './dashboard.scss';
import React, { useEffect, useState } from 'react';
import FetchMovies from './fetchMovie';
import { useParams } from 'react-router';

interface MovieProps {
  trailerUrl: string;
}

//PascalCasing
const Movie = () => {
  const [movieData, setMovieData] = useState([]);
  const [clickedMovieId, setClickedMovieId] = useState(Number);
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
  }, [clickedMovieId]);

  // eslint-disable-next-line no-console
  console.log(id);
  const selectedMovie = movieData.find(movie => movie.id === Number(id));

  return (
    <div>
      {selectedMovie && (
        <div>
          <h1>THIS IS THE ID: {id}</h1>
          <iframe
            title="Embedded Content"
            width="1500"
            height="1000"
            src={selectedMovie.url}
            frameBorder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowFullScreen
          ></iframe>
        </div>
      )}
    </div>
  );
};

export default Movie;