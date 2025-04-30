import React, { useEffect, useState } from "react";
import { ProductCard } from "../ShopComponent/ProductCard";
import axiosFetch from "../../Helper/Axios";

export const ListProduct = () => {
  const [token, setToken] = useState(sessionStorage.getItem("token"));
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true); // Thêm loading state
  const [error, setError] = useState(null);     // Thêm error state

  const fetchData = async () => {
    try {
      const response = await axiosFetch({
        url: "product/",
        method: "GET",
      });

      console.log(response.data);

      if (Array.isArray(response.data)) {
        setData(response.data);
      } else {
        console.error("Unexpected response:", response);
        setData([]); // fallback tránh lỗi
      }
    } catch (err) {
      console.error("Error fetching product data:", err);
      setError("Failed to load products.");
      setData([]); // fallback
    } finally {
      setLoading(false); // Luôn kết thúc loading
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (loading) {
    return <div>Loading products...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <section id="products" className="section product">
      <div className="container">
        <p className="section-subtitle"> -- Laptops --</p>
        <h2 className="text-2xl font-semibold text-center">Unleash peak performance with cutting-edge laptops built for every challenge.</h2>

        <ul className="filter-list">
          {/* Các filter button giữ nguyên */}
          {/* ... */}
        </ul>

        <ul className="grid-list">
          {data.length > 0 ? (
            data.map((item) => (
              <ProductCard
                key={item.productid}
                id={item.productid}
                name={item.productName}
                description={item.description}
                price={item.price}
                img={item.img}  
              />
            ))
          ) : (
            <p>No products available.</p> // Nếu không có sản phẩm
          )}
        </ul>
      </div>
    </section>
  );
};
