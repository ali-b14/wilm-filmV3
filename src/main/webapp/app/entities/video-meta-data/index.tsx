import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import VideoMetaData from './video-meta-data';
import VideoMetaDataDetail from './video-meta-data-detail';
import VideoMetaDataUpdate from './video-meta-data-update';
import VideoMetaDataDeleteDialog from './video-meta-data-delete-dialog';

const VideoMetaDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<VideoMetaData />} />
    <Route path="new" element={<VideoMetaDataUpdate />} />
    <Route path=":id">
      <Route index element={<VideoMetaDataDetail />} />
      <Route path="edit" element={<VideoMetaDataUpdate />} />
      <Route path="delete" element={<VideoMetaDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VideoMetaDataRoutes;
