import {Enum} from "../helpers/Enum.jsx";


const [OPEN, FINISH] = [{value: "OPEN"}, {value: "FINISH"}];
export const Status = new Enum({OPEN, FINISH});

export const invertStatus = (currentStatus) => {
  switch (currentStatus) {
    case Status.OPEN.valueOf():
      return Status.FINISH.valueOf();
    case Status.FINISH.valueOf():
      return Status.OPEN.valueOf();
  }
};
