import React, {PropTypes} from 'react'

const Link = ({active, children, onClick, href}) => {
  if (active) {
    return (
        <li>
          <a href={href} className="selected">
            {children}
          </a>
        </li>
    )
  }

  return (
      <li>
        <a href={href}
           onClick={e => {
             onClick()
           }}
        >
          {children}
        </a>
      </li>
  )
};

Link.propTypes = {
  active: PropTypes.bool.isRequired,
  children: PropTypes.node.isRequired,
  onClick: PropTypes.func.isRequired
};

export default Link
