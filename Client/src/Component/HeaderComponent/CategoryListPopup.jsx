import React, { useState, useEffect } from "react";
import { Link } from "react-scroll";
import { insecureAxiosFetch } from "../../Helper/Axios";

const CategoryListPopup = ({ isVisible }) => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Chỉ gọi API khi component được hiển thị
    if (isVisible) {
      fetchCategories();
    }
  }, [isVisible]);

  const fetchCategories = async () => {
    setLoading(true);
    try {
      // Sử dụng insecureAxiosFetch để gọi API
      const response = await insecureAxiosFetch({
        url: "categories/get?limit=20",
        method: "GET",
      });

      if (response.data) {
        setCategories(response.data);
        setError(null);
      } else {
        throw new Error("Không nhận được dữ liệu");
      }
    } catch (err) {
      setError("Không thể tải danh mục sản phẩm");
      console.error("Error fetching categories:", err);
    } finally {
      setLoading(false);
    }
  };

  // Nếu không visible thì không render gì cả
  if (!isVisible) return null;

  return (
    <div className="fixed left-0 right-0 z-50 border-t border-gray-200 shadow-lg animate-fadeIn">
      <div className="bg-white w-full">
        <div className="container mx-auto py-6 px-4">
          <h3 className="mt-0 mb-4 text-gray-800 text-lg font-medium border-b border-gray-100 pb-2">
            Danh mục sản phẩm
          </h3>
          
          {loading && <p className="text-gray-600">Đang tải...</p>}
          
          {error && <p className="text-red-500">{error}</p>}
          
          {!loading && !error && (
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
              {categories.map((category) => (
                <div key={category.id}>
                  <Link
                    to="products"
                    smooth="linear"
                    spy
                    offset={-30}
                    className="text-gray-700 no-underline block transition-colors hover:text-blue-500 cursor-pointer"
                    onClick={() =>
                      console.log(`Selected category: ${category.categoryName}`)
                    }
                  >
                    {category.categoryName}
                  </Link>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CategoryListPopup;