import React from "react";
import "./map.css";

const MapSearchUI = () => {
  return (
    <div className="map-container">
      {/* <header className="header"></header> */}

      <main className="main">
        <aside className="result-list">
          <div className="search-bar">
            <input type="text" placeholder="장소를 검색하세요" />
            <button>검색</button>
          </div>

          <h2>검색 결과</h2>
          <ul>
            {Array.from({ length: 10 }).map((_, i) => (
              <li key={i}>
                <div className="place-name">장소 이름 {i + 1}</div>
                <div className="place-address">서울시 강남구 어딘가 {i + 1}</div>
              </li>
            ))}
          </ul>
        </aside>

        <div className="map-area">
          <div className="map-placeholder">🗺️ 지도 표시 영역</div>
        </div>
      </main>
    </div>
  );
};

export default MapSearchUI;
