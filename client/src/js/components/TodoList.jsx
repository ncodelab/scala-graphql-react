import React, {PropTypes} from 'react'
import TodoView from './TodoView.jsx'
import Todo from '../data/Todo.jsx';


class TodoList extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    let {todos, onTodoClick, onTodoEdit, onTodoChange, onTodoRemove} = this.props;

    return (
        <section className="main">
          <input className="toggle-all" type="checkbox"/>
          <label htmlFor="toggle-all">Mark all as complete</label>
          <ul className="todo-list">
            {todos.sort((a, b) => a.id - b.id).map(todo =>
                <TodoView
                    key={todo.id}
                    onTodoEdit={() => onTodoEdit(todo.id)}
                    onTodoComplete={(currentStatus) => onTodoClick(todo.id, currentStatus)}
                    onTodoChange={(evt) => onTodoChange(todo.id, evt.target.value)}
                    onTodoRemove={() => onTodoRemove(todo.id)}
                    todo={todo}
                />
            )}
          </ul>
        </section>
    );
  }
}

TodoList.propTypes = {
  todos: PropTypes.arrayOf(React.PropTypes.instanceOf(Todo).isRequired).isRequired,
  onTodoClick: PropTypes.func.isRequired,
  onTodoRemove: PropTypes.func.isRequired,
  onTodoChange: PropTypes.func.isRequired
};

export default TodoList
