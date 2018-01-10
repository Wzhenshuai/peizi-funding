package com.icaopan.stock;

import com.icaopan.admin.other.PermissionConstants;
import com.icaopan.admin.other.ResponseResult;
import com.icaopan.common.CommonAction;
import com.icaopan.stock.bean.StockPoolParams;
import com.icaopan.stock.model.StockPool;
import com.icaopan.stock.model.StockSecurity;
import com.icaopan.stock.service.PoolService;
import com.icaopan.stock.service.SecurityService;
import com.icaopan.util.ImportExcel;
import com.icaopan.util.page.Page;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoyLeong @royleo.xyz on 2016/12/19.
 */
@Controller
@RequestMapping("/pool")
public class PoolAction extends CommonAction {

    @Autowired
    PoolService poolService;

    @Autowired
    SecurityService securityService;

    @RequestMapping("/pool")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER+PermissionConstants.FIND)
    public String poolIndex(ModelMap modelMap){
        modelMap.addAttribute("message","success");
        return "stock/pool";
    }

    @RequestMapping("/find")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public Page find(@Param("aoData") String aoData,@Param("stockName")String stockName,@Param("stockCode")String stockCode,@Param("type")String type,@Param("customerId")Integer customerId){
        Page page = new Page(aoData);
        if (type!=null && type.length()==0){
            type = null;
        }
        if (stockName!=null && stockName.length()==0){
            stockName = null;
        }
        StockPoolParams params = new StockPoolParams(stockName,stockCode,type,customerId);
        page = poolService.findByPage(page,params);
        return page;
    }


    /**
     * 导入数据
     * @param file
     * @return
     * */
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public String importFile(@RequestParam("excelFile") MultipartFile file,@RequestParam("typeImport")String type,ModelMap modelMap){
        List<StockPool> pools = new ArrayList<StockPool>();
        try {
            ImportExcel ie = new ImportExcel();
            File file1 = ie.multipartToFile(file);
            List<List<Object>> list = ie.readExcel(file1);
            for (int i = 1; i < list.size(); i++) {
                List<Object> obj = list.get(i);
                String internalId = obj.get(0).toString().split("\\.")[0];
                if (internalId==null||internalId.length()==0){
                    continue;
                }
                StockPool sp = new StockPool();
                sp.setStockCode(internalId);
                StockSecurity stockSecurity = securityService.findStockSecurityById(internalId);
                if (stockSecurity==null||stockSecurity.getName()==null){
                    String notes = "根据股票ID："+internalId+" 无法找到对应的股票信息";
                    logger.info(notes);
                    modelMap.addAttribute("message",notes);
                    return "stock/pool";
                }
                sp.setStockName(stockSecurity.getName());
                sp.setType(type);
                pools.add(sp);
            }
            boolean bool  = poolService.saveBatch(pools);
            if (bool){
                modelMap.addAttribute("message", "success");
            }else {
                modelMap.addAttribute("message", "导入数据失败");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            modelMap.addAttribute("message","导入数据失败，请检查导入数据的正确性");
        }catch (IOException e){
            e.printStackTrace();
            modelMap.addAttribute("message", "导入数据失败，请检查导入数据的正确性");
        }catch (Exception e){
            e.printStackTrace();
            modelMap.addAttribute("message", "导入数据失败，请检查导入数据的正确性");
        }
        return "stock/pool";
    }

    /**
     * 下载导入用户数据模板
     * @param response
     * @param
     * */
    @RequestMapping(value = "/import/template")
    @ResponseBody
    @RequiresUser
    @RequiresPermissions(value=PermissionConstants.P_USER+PermissionConstants.FIND)
    public void exportPlacement(HttpServletResponse response){
        Page page = poolService.findByPage(new Page(), new StockPoolParams());
        JSONArray data=JSONArray.fromObject(page.getAaData());
        com.icaopan.util.ExcelUtil.exportExcel("证券信息",
                new String[]{"股票代码"},
                new String[]{"stockCode"},
                data,
                response,
                "yyyy-MM-dd HH:mm:ss");
    }

    @RequestMapping("/edit")
    @RequiresUser
    @RequiresPermissions(value = PermissionConstants.P_USER+PermissionConstants.FIND)
    public String edit(@RequestParam("id")Integer id,Model model){
        StockPool stockPool = poolService.findByPrimaryId(id);
        model.addAttribute("stockPool",stockPool);
        return "stock/editPool";
    }

    @RequestMapping("/verify")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public ResponseResult verify(@RequestParam("stockCode")String code,@RequestParam("stockName")String name){
        if (code!=null && poolService.containCode(code))
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "股票编号已存在", null);
        if (name!=null && poolService.containName(name))
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "股票名已存在", null);
        return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "", null);
    }

    @RequestMapping("/save")
    @RequiresUser
    @ResponseBody
    @RequiresPermissions(value = PermissionConstants.P_USER + PermissionConstants.FIND)
    public ResponseResult save(StockPool stockPool){
        boolean bool = poolService.save(stockPool);
        if (bool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "保存成功", null);
        }else{
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "保存失败，请重试", null);
        }
    }

    @RequestMapping("/update")
    @RequiresUser
    @ResponseBody
    public ResponseResult update(StockPool stockPool){
        boolean bool = poolService.update(stockPool.getId(),stockPool.getStockName(),stockPool.getStockCode(),null,null);
        if (bool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "更新信息成功", null);
        }else{
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "更新信息失败，请重试", null);
        }
    }

    @RequestMapping("/delBatch")
    @RequiresUser
    @ResponseBody
    public ResponseResult delBatch(@RequestParam("delIds")Integer[] ids){
        boolean bool = poolService.deleteBatch(ids);
        if (bool){
            return new ResponseResult(ResponseResult.NORMAL, ResponseResult.SUCCESS, "操作成功", null);
        }else{
            return new ResponseResult(ResponseResult.ERROR, ResponseResult.FAIL, "批量删除失败，请重试", null);
        }
    }

}
