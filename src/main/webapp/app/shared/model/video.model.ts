import { IVideoMetaData } from 'app/shared/model/video-meta-data.model';
import { IUser } from 'app/shared/model/user.model';

export interface IVideo {
  id?: number;
  url?: string | null;
  metaData?: IVideoMetaData | null;
  uploader?: IUser | null;
}

export const defaultValue: Readonly<IVideo> = {};
