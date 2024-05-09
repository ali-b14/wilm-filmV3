import { IVideo } from 'app/shared/model/video.model';
import { IUser } from 'app/shared/model/user.model';

export interface IWatchLater {
  id?: number;
  video?: IVideo | null;
  userProfile?: IUser | null;
}

export const defaultValue: Readonly<IWatchLater> = {};
