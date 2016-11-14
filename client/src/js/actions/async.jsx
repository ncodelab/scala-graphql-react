import {store} from '../reducers/index.jsx'
import * as state from "./index.jsx";
import * as gql from "../graphql/index.jsx";

const defaultDispatch = (gdlFunction, dataProcessor = state.folderState) => {
  store.dispatch((dispatch) => {
    gdlFunction.then((data) => {
      dispatch(dataProcessor(data))
    })
  })
};

export const bootstrapData = () =>
    defaultDispatch(
        gql.extractFolderContent("1"),
        state.synchronousState);


export const createTask = (text, title) =>
    defaultDispatch(gql.createTodo(text, title));


export const setStatus = (taskId, status) =>
  defaultDispatch(gql.setStatus(taskId, status));

export const changeTodo = (taskId, text) =>
  defaultDispatch(gql.changeTodo(taskId, text));

export const removeTodo = (taskId) =>
  defaultDispatch(gql.removeTodo(taskId));
