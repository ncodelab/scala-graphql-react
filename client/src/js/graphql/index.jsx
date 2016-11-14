import {Status} from "../data/Status.jsx";
import Todo from "../data/Todo.jsx";
import * as TaskQuery from "../queries/TaskQuery.jsx";
import * as FolderQuery from "../queries/FolderQuery.jsx";
import Folder from "../data/Folder.jsx";


const nop = (a) => a;

const graphQlClient = require('graphql-client')({
  url: 'http://127.0.0.1:8081/graphql',
  headers: {}
});

const extractTasks = (tasksArray) => {
  return tasksArray.map(({id, text, title, status}) => {
    return new Todo(id, title, text, Status.byValue(status));
  });
};

const extractFolder = (data) => {
  let tasks = extractTasks(data.tasks);
  return new Folder(data.id, data.name, tasks);
};


const extractState = (filedName) => (body) => {
  let folder = body.data[filedName];
  let state = extractFolder(folder);
  return state;
};

const catchPromise = (promise) => {
  return promise.catch(function (err) {
    console.error(err)
  })
};

const run = (query) => catchPromise(graphQlClient.query(query, {}, nop));

export const extractFolderContent = (id) => {
  return run(FolderQuery.getFolder(id.toString())).then(extractState("getFolder"))
};

export const createTodo = (text, title) => {
  return run(TaskQuery.createTask(1, text, title)).then(extractState("addTask"))
};

export const removeTodo = (taskId) => {
  return run(TaskQuery.removeTask(1, taskId)).then(extractState("removeTask"))
};

export const changeTodo = (taskId, text) => {
  return run(TaskQuery.setTaskText(1, taskId, text)).then(extractState("setTaskText"))
};

export const setStatus = (taskId, status) => {
  return run(TaskQuery.setStatus(1, taskId, status)).then(extractState("setStatus"))
};
