import React from 'react'
import AddTodo from '../containers/AddTodo.jsx'

class Header extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
        <div className="header">
          <h1>todos</h1>
          <AddTodo />
        </div>
    )
  }
}


export default Header
