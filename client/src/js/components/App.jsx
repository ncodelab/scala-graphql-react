import React from 'react'
import Footer from './Footer.jsx'
import Header from './Header.jsx'
import AddTodo from '../containers/AddTodo.jsx'
import VisibleTodoList from '../containers/VisibleTodoList.jsx'

const App = () => (
    <div>
      <Header />
      <VisibleTodoList />
      <Footer />
    </div>
);

export default App
