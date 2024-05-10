// import './dashboard.scss';
// import React from 'react';

// interface Movie {
//   img: string;
//   title: string;
//   desc: string;
//   id: number;
//   trailerUrl?: string;
// }

// interface Category {
//   title: string;
//   url: string;
// }

// const playVideo = url => {
//   // Ensure the URL is complete and includes the YouTube base URL if not already included
//   const fullUrl = url.startsWith('http') ? url : `https://www.youtube.com/watch?v=${url}`;
//   window.open(fullUrl, '_blank');
// };

// function MovieGrid() {
//   return (
//     <div className="movie-grid-container">
//       <h3>{title}</h3>
//       <div className="movie-grid">
//         {movies.map(movie => (
//           <div
//             key={movie.id}
//             className="movie-item"
//             onClick={() => movie.trailerUrl && playVideo(`https://www.youtube.com/watch?v=${movie.trailerUrl}`)}
//           >
//             <img src={movie.img} alt={movie.title} className="movie-image" />
//             <div className="movie-title-overlay">{movie.title}</div>
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// };

// export default MovieGrid;
