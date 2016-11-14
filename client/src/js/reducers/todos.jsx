import {Event} from "../actions/index.jsx"
import * as Async from "../actions/async.jsx";


const todos = (state = [], action) => {
  switch (action.type) {

    case Event.BOOTSTRAP.valueOf():
      state = action.state;
      return state;

    case Event.STATE.valueOf():
      state = action.state;
      return state;

    case Event.LOCAL_EDIT.valueOf():
      return state.map((task) => {
        if (task.id === action.id) {
          task.setText(action.text)
        }
        return task;
      });

    case Event.EDIT_FLAG.valueOf():
      return state.map((task) => {
        if (task.id === action.id) {
          task.setEditing(!task.editing);
          if (!task.editing) {
            Async.changeTodo(task.id, task.text)
          }
        }
        return task;
      });

    default:
      return state
  }
};

export default todos
