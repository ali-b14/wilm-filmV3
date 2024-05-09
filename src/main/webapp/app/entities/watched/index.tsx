import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Watched from './watched';
import WatchedDetail from './watched-detail';
import WatchedUpdate from './watched-update';
import WatchedDeleteDialog from './watched-delete-dialog';

const WatchedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Watched />} />
    <Route path="new" element={<WatchedUpdate />} />
    <Route path=":id">
      <Route index element={<WatchedDetail />} />
      <Route path="edit" element={<WatchedUpdate />} />
      <Route path="delete" element={<WatchedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WatchedRoutes;
