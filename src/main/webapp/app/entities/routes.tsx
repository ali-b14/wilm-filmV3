import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Video from './video';
import VideoMetaData from './video-meta-data';
import Comment from './comment';
import Like from './like';
import Watched from './watched';
import WatchLater from './watch-later';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="video/*" element={<Video />} />
        <Route path="video-meta-data/*" element={<VideoMetaData />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="like/*" element={<Like />} />
        <Route path="watched/*" element={<Watched />} />
        <Route path="watch-later/*" element={<WatchLater />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
