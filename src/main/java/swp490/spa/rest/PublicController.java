package swp490.spa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import swp490.spa.dto.responses.LoginResponse;
import swp490.spa.dto.support.Conversion;
import swp490.spa.dto.helper.ResponseHelper;
import swp490.spa.dto.support.Response;
import swp490.spa.entities.*;
import swp490.spa.jwt.JWTUtils;
import swp490.spa.services.*;
import swp490.spa.services.SpaService;

@RestController
//@RequestMapping("/api/public")
@RequestMapping("public")
@CrossOrigin
public class PublicController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ManagerService managerService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpaService spaService;
    @Autowired
    private SpaAddressService spaAddressService;
    @Autowired
    private SpaServiceService spaServiceService;
    @Autowired
    JWTUtils jwtUtils;
    private Conversion conversion;

    public PublicController(UserService userService, CategoryService categoryService,
                            SpaService spaService, SpaAddressService spaAddressService,
                            SpaServiceService spaServiceService, CustomerService customerService,
                            StaffService staffService, ManagerService managerService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.spaService = spaService;
        this.spaAddressService = spaAddressService;
        this.spaServiceService = spaServiceService;
        this.customerService = customerService;
        this.staffService = staffService;
        this.managerService = managerService;
        this.conversion = new Conversion();
    }

    @GetMapping("/category")
    public Response findCategoryByStatus(@RequestParam String status, Pageable pageable){
        Page<Category> categories = categoryService.findAllByStatus(status, pageable);
        if (!categories.hasContent() && !categories.isFirst()) {
            categories = categoryService.findAllByStatus(status,
                    PageRequest.of(categories.getTotalPages()-1, categories.getSize(), categories.getSort()));
        }
        return ResponseHelper.ok(conversion.convertToCategoryResponse(categories));
    }

    @GetMapping("/user")
    public Response findUserByPhone(@RequestParam String phone){
        User user = userService.findByPhone(phone);
        return ResponseHelper.ok(user);
    }

    @GetMapping("/spa")
    public Response findSpaByStatusAvailable(Pageable pageable){
        Page<Spa> spas = spaService.findByStatus(Status.AVAILABLE,pageable);
        if(!spas.hasContent() && !spas.isFirst()){
            spas = spaService.findByStatus(Status.AVAILABLE,
                    PageRequest.of(spas.getTotalPages()-1, spas.getSize(), spas.getSort()));
        }
        return ResponseHelper.ok(conversion.convertToSpaResponse(spas));
    }

    @GetMapping("/spaaddress")
    public Response findSpaAddressBySpaId(@RequestParam Integer spaId, Pageable pageable){
        Page<SpaAddress> spaAddresses = spaAddressService.findBySpaId(spaId, pageable);
        if(!spaAddresses.hasContent() && !spaAddresses.isFirst()){
            spaAddresses = spaAddressService.findBySpaId(spaId,
                    PageRequest.of(spaAddresses.getTotalPages()-1, spaAddresses.getSize(), spaAddresses.getSort()));
        }
        return ResponseHelper.ok(conversion.convertToSpaAddressResponse(spaAddresses));
    }

    @GetMapping("/spaservice")
    public Response findSpaServiceBySpaId(@RequestParam Integer spaId, @RequestParam Status status, Pageable pageable){
        Page<swp490.spa.entities.SpaService> spaServices = spaServiceService.findBySpaIdAndStatus(spaId, status, pageable);
        if(!spaServices.hasContent() && !spaServices.isFirst()){
            spaServices = spaServiceService.findBySpaIdAndStatus(spaId, status,
                    PageRequest.of(spaServices.getTotalPages()-1, spaServices.getSize(), spaServices.getSort()));
        }
        return ResponseHelper.ok(conversion.convertToSpaServiceResponse(spaServices));
    }

    @PostMapping("/login")
    public LoginResponse login (@RequestBody AuthRequest account){

        User newAccount = userService.findByPhone(account.getPhone());

        if(newAccount == null){
            return LoginResponse.createErrorResponse(LoginResponse.Error.USERNAME_NOT_FOUND);
        }

        if(!newAccount.getPassword().equals(account.getPassword())){
            return LoginResponse.createErrorResponse(LoginResponse.Error.WRONG_PASSWORD);
        }

        boolean isExisted = false;

        switch (account.getRole()){
            case CUSTOMER:
                Customer customer = customerService.findByUserId(newAccount.getId());
                if(customer!=null){
                    isExisted = true;
                }
                break;
            case MANAGER:
                 Manager manager = managerService.findManagerById(newAccount.getId());
                if(manager!=null){
                    isExisted = true;
                }
                break;
            case STAFF:
                Staff staff = staffService.findByStaffId(newAccount.getId());
                if(staff!=null){
                    isExisted = true;
                }
                break;
        }

        if(isExisted == false){
            return LoginResponse.createErrorResponse(LoginResponse.Error.ROLE_NOT_EXISTED);
        }

        String role = account.getRole().toString();
        String token = jwtUtils.generateToken(newAccount.getPhone(), role);
        int userId = newAccount.getId();
        return LoginResponse.createSuccessResponse(token,role,userId);
    }

}
