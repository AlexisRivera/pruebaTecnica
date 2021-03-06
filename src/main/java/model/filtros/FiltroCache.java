package model.filtros;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Esta clase fuerza a que todos los recursos que se utilizan en la aplicacion no tengan cache y siempre se carguen
 * esto no incluye archivos txt, pdf y csv
 * @generated
 */
@WebFilter(filterName = "FiltroCache", urlPatterns = {"*.html","/partials/*", "/js/*", "/webresources/*"})
public class FiltroCache implements Filter {
    
    public FiltroCache() {
    }    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //no realiza ninguna accion
    }
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        //excluir los archivos que se descargan
        if(((HttpServletRequest)request).getRequestURI().endsWith(".pdf")
                ||((HttpServletRequest)request).getRequestURI().endsWith(".txt")
                ||((HttpServletRequest)request).getRequestURI().endsWith(".cvs")){
            chain.doFilter(request, response);
            return;
        }
        
        //incluir encabezados para cache
        ((HttpServletResponse)response).addHeader("cache-control", "max-age=0");
        ((HttpServletResponse)response).addHeader("cache-control", "no-cache");
        ((HttpServletResponse)response).addHeader("expires", "0");
        ((HttpServletResponse)response).addHeader("expires", "Tue, 01 Jan 1980 1:00:00 GMT");
        ((HttpServletResponse)response).addHeader("pragma", "no-cache");
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //no realiza ninguna accion
    }
}
