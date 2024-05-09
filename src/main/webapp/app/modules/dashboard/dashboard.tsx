import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './dashboard.scss';

interface Movie {
  img: string;
  title: string;
  desc: string;
  id: number;
  trailerUrl?: string;
}

interface Category {
  title: string;
  url: string;
}

const Dashboard: React.FC = () => {
  const [mainMovie, setMainMovie] = useState<Movie | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);

  const apiKey = '72a82be4ce584fe790c0071997f35a73';

  const playVideo = url => {
    // Ensure the URL is complete and includes the YouTube base URL if not already included
    const fullUrl = url.startsWith('http') ? url : `https://www.youtube.com/watch?v=${url}`;
    window.open(fullUrl, '_blank');
  };

  useEffect(() => {
    fetchMainMovie(550);

    const categoryUrls: Category[] = [
      { title: 'Upcoming', url: `https://api.themoviedb.org/3/movie/upcoming?api_key=${apiKey}&language=en-US&page=1` },
      { title: 'Popular', url: `https://api.themoviedb.org/3/movie/popular?api_key=${apiKey}&language=en-US&page=1` },
      { title: 'Now Playing', url: `https://api.themoviedb.org/3/movie/now_playing?api_key=${apiKey}&language=en-US&page=1` },
      { title: 'Top Rated', url: `https://api.themoviedb.org/3/movie/top_rated?api_key=${apiKey}&language=en-US&page=1` },
      { title: 'Action', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=28` },
      { title: 'Comedy', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=35` },
      {
        title: 'Horror',
        url: `https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&language=en-US&query=horror&page=1&include_adult=false`,
      },
      { title: 'Romance', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=10749` },
      { title: 'Sci-Fi', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=878` },
      { title: 'Thriller', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=53` },
      { title: 'Adventure', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=12` },
      { title: 'Fantasy', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=14` },
      { title: 'Documentary', url: `https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_genres=99` },
    ];

    setCategories(categoryUrls);
  }, []);

  const fetchMainMovie = async (id: number) => {
    try {
      const url = `https://api.themoviedb.org/3/movie/${id}?api_key=${apiKey}&append_to_response=videos`;
      const response = await axios.get(url);
      const movieData: Movie = {
        img: `https://image.tmdb.org/t/p/original${response.data.poster_path}`,
        title: response.data.title,
        desc: response.data.overview,
        id,
        trailerUrl: response.data.videos.results.find((v: any) => v.site === 'YouTube' && v.type === 'Trailer')?.key,
      };
      setMainMovie(movieData);
    } catch (error) {
      console.error('Error loading the main movie:', error);
      setMainMovie(null);
    }
  };

  return (
    <div>
      <div className="header-content">WilFilm</div>
      <div className="container">
        {mainMovie && (
          <div className="movie-highlight">
            <img src={mainMovie.img} alt="Main Movie" />
            <div className="movie-info">
              <h2>{mainMovie.title}</h2>
              <p>{mainMovie.desc}</p>
              <button
                className="btn btn-primary"
                onClick={() => mainMovie.trailerUrl && playVideo(`https://www.youtube.com/watch?v=${mainMovie.trailerUrl}`)}
              >
                Play
              </button>
              <button className="btn btn-secondary">Watch Later</button>
            </div>
          </div>
        )}
        {categories.map(category => (
          <MovieCategory key={category.title} title={category.title} url={category.url} playVideo={playVideo} />
        ))}
      </div>
    </div>
  );
};

const MovieCategory: React.FC<{ title: string; url: string; playVideo: (url: string) => void }> = ({ title, url, playVideo }) => {
  const [movies, setMovies] = useState<Movie[]>([]);

  useEffect(() => {
    const fetchMovies = async () => {
      try {
        const response = await axios.get(url);
        const moviesData: Movie[] = response.data.results.map((movie: any) => ({
          img: `https://image.tmdb.org/t/p/w500${movie.poster_path}`,
          title: movie.title,
          desc: movie.overview,
          id: movie.id,
          trailerUrl: movie.videos?.results?.find((v: any) => v.site === 'YouTube' && v.type === 'Trailer')?.key,
        }));
        setMovies(moviesData);
      } catch (error) {
        console.error('Error loading the movies:', error);
      }
    };
    fetchMovies();
  }, [url]);

  return (
    <div className="movie-grid-container">
      <h3>{title}</h3>
      <div className="movie-grid">
        {movies.map(movie => (
          <div
            key={movie.id}
            className="movie-item"
            onClick={() => movie.trailerUrl && playVideo(`https://www.youtube.com/watch?v=${movie.trailerUrl}`)}
          >
            <img src={movie.img} alt={movie.title} className="movie-image" />
            <div className="movie-title-overlay">{movie.title}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Dashboard;
