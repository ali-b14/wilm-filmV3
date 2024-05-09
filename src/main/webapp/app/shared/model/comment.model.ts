import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IVideo } from 'app/shared/model/video.model';

export interface IComment {
  id?: number;
  text?: string;
  postedAt?: dayjs.Dayjs;
  author?: IUser | null;
  video?: IVideo | null;
}

export const defaultValue: Readonly<IComment> = {};
