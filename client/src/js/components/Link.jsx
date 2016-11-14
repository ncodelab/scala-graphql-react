import React, {PropTypes} from 'react'


class Link extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let {active, children, onClick, href} = this.props;
    return (
        <li>
          <a href={href} className={active ? "selected" : ""}
             onClick={e => {
               onClick()
             }}
          >
            {children}
          </a>
        </li>
    );
  }

  static get defaultProps() {
    return {}
  }
}

Link.propTypes = {
  active: PropTypes.bool.isRequired,
  children: PropTypes.node.isRequired,
  onClick: PropTypes.func.isRequired
};

export default Link
