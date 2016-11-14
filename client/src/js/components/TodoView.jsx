import React, {PropTypes} from 'react'
import TodoData from "../data/Todo.jsx";
let classNames = require('classnames');

class TodoView extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    let {onTodoEdit, onTodoComplete, onTodoChange, onTodoRemove, todo} = this.props;

    let text = todo.text;
    let completed = todo.isCompleted();

    let liClasses = classNames({
      'completed': completed,
      'editing': todo.editing
    });

    return (
        <li className={liClasses}>
          <div className="view">
            <input className="toggle" type="checkbox"
                   onClick={() => onTodoComplete(todo.status.valueOf())}
                   checked={completed}/>
            <label onClick={onTodoEdit}>{text}</label>
            <button className="destroy" onClick={onTodoRemove}></button>
          </div>
          <input className="edit" value={text}
                 onChange={onTodoChange}
                 onKeyPress={(evt) => {
                   if (evt.nativeEvent.keyCode == 13) {
                     onTodoEdit();
                   }
                 }}/>
        </li>
    )
  }
}


TodoView.propTypes = {
  onTodoRemove: PropTypes.func.isRequired,
  onTodoChange: PropTypes.func.isRequired,
  onTodoEdit: PropTypes.func.isRequired,
  onTodoComplete: PropTypes.func.isRequired,
  todo: PropTypes.instanceOf(TodoData).isRequired
};

export default TodoView
