# DUAN_QUANLYBANHANG
Dự án thi cuối kỳ 2 môn Lập trình thiết bị di động 
App quản lý bán hàng sử dụng Firebase và Android Studio
1. Trang đầu tiên là trang login
   - Đăng nhập bằng email và password được tạo sẵn trong Firebase. Đăng nhập thành công  sẽ được chuyển sang trang chủ.
   
<img width="362" height="813" alt="Ảnh chụp màn hình 2026-05-30 103926" src="https://github.com/user-attachments/assets/e827291e-9950-4fb3-bafa-a291451aaca1" />
<img width="366" height="815" alt="Ảnh chụp màn hình 2026-05-30 104108" src="https://github.com/user-attachments/assets/de6ec75d-bcfc-4d47-b9f8-5b34115ea8f5" />

2. Trang chủ
   - Trang giới thiệu có banner slider và bottom navegation.
        + Banner slider: các ảnh sẽ được tự động chuyển sau 3s.
        + Bottom navegation: các item khi click vào sẽ chuyển sang các trang đã cài đặt tương ứng.
          
<img width="363" height="817" alt="Ảnh chụp màn hình 2026-05-30 124904" src="https://github.com/user-attachments/assets/02343889-8ab2-42fb-b208-80b6de8606a2" />
<img width="359" height="818" alt="Ảnh chụp màn hình 2026-05-30 124813" src="https://github.com/user-attachments/assets/8eaa9629-cbdc-4c11-a91e-74be6237e2e2" />
<img width="370" height="823" alt="Ảnh chụp màn hình 2026-05-30 124714" src="https://github.com/user-attachments/assets/196ef7fa-af73-4dbe-99dd-d611623122ee" />
<img width="371" height="820" alt="Ảnh chụp màn hình 2026-05-30 124555" src="https://github.com/user-attachments/assets/9db315ba-5aa8-4921-a26e-ed90b3ab6f93" />

3. Trang Order
   - Chọn bàn sẽ chuyển sang trang order, đầu tiên ta click vào bàn cần chọn, sẽ chuyển sang trang menu nước, chọn nước và số lượng khách order. Button chọn lại là xóa hết các thao tác chọn nước vừa thêm. Thanh toán sẽ chuyển sanng trang hóa đơn, trang này có thể tùy giảm số lượng và total sẽ tự động cập nhật lại khi thêm giảm số lượng. Khi thanh toán xong thì data đơn hàng sẽ tự động được lưu vào Firebase.
     
<img width="361" height="805" alt="image" src="https://github.com/user-attachments/assets/4f0ee586-2e89-4a13-8eae-9f25d442daa8" />
<img width="361" height="818" alt="image" src="https://github.com/user-attachments/assets/3e17b01b-4221-452a-a0e1-341ba4f2d29a" />
<img width="361" height="817" alt="image" src="https://github.com/user-attachments/assets/1b5d7f7a-0a5d-4f00-8601-20df634eb268" />

4. Trang quản lý
   - Trang quản lý hiển thị tổng doanh thu, tổng số đơn, các đơn gần đây và các món nước bán chạy.
   
5. Trang lịch sử đơn hàng
    - Trang lịch sử đơn hàng sẽ hiển thị các đơn hàng từ mới nhất đến cũ nhất theo tứ tự thới gian. Data được lấy từ Firebase.
        
<img width="366" height="814" alt="image" src="https://github.com/user-attachments/assets/1067d7bd-fcf2-4eb2-a442-8199dd0eb055" />
<img width="365" height="821" alt="image" src="https://github.com/user-attachments/assets/c0bd1c9f-2b3f-4708-a054-7da485074396" />
<img width="362" height="819" alt="image" src="https://github.com/user-attachments/assets/4a5280b8-90c4-4487-a1ff-4834472b2429" />
<img width="366" height="816" alt="image" src="https://github.com/user-attachments/assets/4063ec00-1e72-4623-af3a-09c3d180fe2b" />

6. Trang đăng xuất
   - Các item như chọn bàn, lịch sử đơn hàng, báo cáo doanh thu thì khi click vào sẽ chuyển sang các trang tương ứng là trang order, trang hiển thị lịch sử đơn hàng và trang quản lý.
   - Đăng xuất: khi click vào sẽ đăng xuất tài khoản và chuyển sang trang login.
      
<img width="371" height="818" alt="image" src="https://github.com/user-attachments/assets/05b698e9-42b2-4f07-a133-7b7cf69b480c" />

   
