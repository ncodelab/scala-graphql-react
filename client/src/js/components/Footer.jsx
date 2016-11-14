import React from 'react'
import FilterLink from '../containers/FilterLink.jsx'

const Footer = () => (
    <footer className="footer">
      <ul className="filters">
        <FilterLink filter="SHOW_ALL" href="#/all">
          All
        </FilterLink>
        <FilterLink filter="SHOW_ACTIVE" href="#/active">
          Active
        </FilterLink>
        <FilterLink filter="SHOW_COMPLETED" href="#/completed">
          Completed

        </FilterLink>
      </ul>
    </footer>
);

export default Footer
