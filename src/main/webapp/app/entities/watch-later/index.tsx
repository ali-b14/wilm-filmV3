import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WatchLater from './watch-later';
import WatchLaterDetail from './watch-later-detail';
import WatchLaterUpdate from './watch-later-update';
import WatchLaterDeleteDialog from './watch-later-delete-dialog';

const WatchLaterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WatchLater />} />
    <Route path="new" element={<WatchLaterUpdate />} />
    <Route path=":id">
      <Route index element={<WatchLaterDetail />} />
      <Route path="edit" element={<WatchLaterUpdate />} />
      <Route path="delete" element={<WatchLaterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WatchLaterRoutes;
