import {connect} from 'react-redux'
import { editTodo, localTextEdit } from '../actions/index.jsx'
import TodoList from '../components/TodoList.jsx'
import * as Async from "../actions/async.jsx";
import {invertStatus} from "../data/Status.jsx";


const getVisibleTodos = (todos, filter) => {
  switch (filter) {
    case 'SHOW_ALL':
      return todos;
    case 'SHOW_COMPLETED':
      return todos.filter(t => t.isCompleted());
    case 'SHOW_ACTIVE':
      return todos.filter(t => !t.isCompleted());
  }
};

const mapStateToProps = (state) => {
  return {
    todos: getVisibleTodos(state.todos, state.visibilityFilter)
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    onTodoClick: (id, currentStatus) => {
      Async.setStatus(id, invertStatus(currentStatus));
    },
    onTodoChange: (id, text) => {
      dispatch(localTextEdit(id, text));
    },
    onTodoRemove: (id) => {
      Async.removeTodo(id);
    },
    onTodoEdit: (id) => {
      dispatch(editTodo(id))
    }
  }
};

const VisibleTodoList = connect(
    mapStateToProps,
    mapDispatchToProps
)(TodoList);

export default VisibleTodoList
