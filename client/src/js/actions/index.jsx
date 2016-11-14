require("./async.jsx");

import {Enum} from "../helpers/Enum.jsx";

const [BOOTSTRAP, STATE, SET_VISIBILITY_FILTER, EDIT_FLAG, LOCAL_EDIT] = [
  {value: "BOOTSTRAP"},
  {value: "STATE"},
  {value: "SET_VISIBILITY_FILTER"},
  {value: "EDIT_FLAG"},
  {value: "LOCAL_EDIT"}];

export const Event = new Enum({BOOTSTRAP, STATE, SET_VISIBILITY_FILTER, EDIT_FLAG, LOCAL_EDIT});

export const state = (event, state) => {
  return {
    type: event.valueOf(),
    state
  }
};

export const synchronousState = (folder) => state(Event.BOOTSTRAP, folder.tasks);

export const folderState = (folder) => state(Event.STATE, folder.tasks);

export const setVisibilityFilter = (filter) => state(Event.SET_VISIBILITY_FILTER, filter);

export const localTextEdit = (id, text) => {
  return {
    type: Event.LOCAL_EDIT.valueOf(),
    id,
    text
  }
};

export const editTodo = (id) => {
  return {
    type: Event.EDIT_FLAG.valueOf(),
    id
  }
};
