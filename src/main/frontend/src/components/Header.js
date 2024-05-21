import React from 'react';
import '../styles/styles.css';

const Header = ({ onCategorySelect }) => {
    return (
        <header>
            <div className="logo">CatchWeak</div>
            <nav className="nav-menu">
                <a href="#" onClick={() => onCategorySelect('연예')}>연예</a>
                <a href="#" onClick={() => onCategorySelect('정치')}>정치</a>
                <a href="#" onClick={() => onCategorySelect('경제')}>경제</a>
                <a href="#" onClick={() => onCategorySelect('사회')}>사회</a>
                <a href="#" onClick={() => onCategorySelect('생활/문화')}>생활/문화</a>
                <a href="#" onClick={() => onCategorySelect('IT/과학')}>IT/과학</a>
            </nav>
            <div className="header-right">
                <div className="search-bar">
                    <input type="text" placeholder="Search..." />
                </div>
                <div className="user-menu">
                    <a href="#">Login</a>
                </div>
            </div>
        </header>
    );
};

export default Header;
